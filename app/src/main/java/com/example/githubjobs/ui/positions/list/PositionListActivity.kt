package com.example.githubjobs.ui.positions.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.lifecycle.Observer
import com.example.githubjobs.R
import com.example.githubjobs.data.local.model.Position
import com.example.githubjobs.databinding.ActivityPositionListBinding
import com.example.githubjobs.ui.base.BaseActivity
import com.example.githubjobs.ui.positions.details.PositionDetailsActivity
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_position_list.*
import org.jetbrains.anko.intentFor
import org.koin.standalone.inject
import java.util.concurrent.TimeUnit

class PositionListActivity : BaseActivity<PositionListViewModel, ActivityPositionListBinding>() {

    override val viewModel: PositionListViewModel by inject()
    override val layoutId = R.layout.activity_position_list

    private val adapter = PositionsAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpAdapter()
        setUpRecyclerView()
        setUpSwipeRefreshLayout()
        refreshPositionList()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        (menu?.findItem(R.id.search)?.actionView as SearchView).apply {
            isIconified = false
            // Créer un observable à partir des évenement changement de texte du champs de recherche
            Observable.create<String> {
                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    // Réduire le champs à la soumission
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        isIconified = true
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        // Emettre le texte saisit dans l'observable
                        newText?.let { t -> it.onNext(t) }
                        return true
                    }

                })
            }
                .compose(debounceInput())
                .subscribe { viewModel.search(it) }

            // Quand on ferme l'input de recherche, annuler la recherche
            setOnCloseListener {
                viewModel.resetSearch()
                false
            }
        }

        val favoritesItem = menu.findItem(R.id.favorites)
        // Afficher le bon icone favori en fonction de la valeur émise par le LiveData
        viewModel.getFavoritesOnly().observe(this, Observer {
            favoritesItem.icon = getDrawable(
                if (it) R.drawable.ic_star else R.drawable.ic_star_border
            )
        })
        return true
    }

    private fun debounceInput(): ObservableTransformer<String, String> {
        return ObservableTransformer { chain ->
            chain
                .map { it.trim().toLowerCase() }
                // N'émettre que si rien n'a été saisi dans les dernière 300ms
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.favorites -> onFavoritesClick()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onFavoritesClick() {
        viewModel.toggleFavoritesOnly()
    }

    private fun setUpAdapter() {
        viewModel.getPositions().observe(this, Observer { adapter.submitList(it) })
        adapter.setOnItemClickListener(this::showPositionDetails)
    }

    private fun setUpRecyclerView() {
        binding.list.adapter = adapter
    }

    private fun refreshPositionList() {
        viewModel.refreshPositions(
            this::onRefresh,
            this::onRefreshError
        )
    }

    private fun setUpSwipeRefreshLayout() {
        swipeLayout.setOnRefreshListener {
            refreshPositionList()
        }
        viewModel.getLoading().observe(this, Observer {
            swipeLayout.isRefreshing = it
        })
    }

    private fun onRefreshError(err: Throwable) {
        err.printStackTrace()
        swipeLayout.isRefreshing = false
    }

    private fun onRefresh() {
        swipeLayout.isRefreshing = false
    }

    private fun showPositionDetails(position: Position) {
        startActivity(
            intentFor<PositionDetailsActivity>(
                PositionDetailsActivity.POSITION_ID to position.id
            )
        )
    }
}
