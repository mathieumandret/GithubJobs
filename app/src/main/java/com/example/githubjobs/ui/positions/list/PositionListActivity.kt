package com.example.githubjobs.ui.positions.list

import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.lifecycle.Observer
import com.example.githubjobs.R
import com.example.githubjobs.data.local.model.Position
import com.example.githubjobs.databinding.ActivityPositionListBinding
import com.example.githubjobs.ui.base.BaseActivity
import com.example.githubjobs.ui.positions.details.PositionDetailsActivity
import io.reactivex.Observable
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
                // Ne pas prendre en compte les strings vides
                .filter { it.isNotBlank() }
                .map { it.trim().toLowerCase() }
                // N'émettre que si rien n'a été saisi dans les dernière 300ms
                .debounce(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewModel.search(it) }
        }
        return true
    }

    private fun setUpAdapter() {
        viewModel.displayedPositions.observe(this, Observer { adapter.submitList(it) })
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
