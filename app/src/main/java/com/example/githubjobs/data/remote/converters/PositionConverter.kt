package com.example.githubjobs.data.remote.converters

import com.example.githubjobs.data.local.model.Position
import com.example.githubjobs.data.remote.model.PositionResponse
import com.example.githubjobs.util.API_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*

class PositionConverter {
    fun convert(response: PositionResponse): Position = Position(
        type = response.type,
        url = response.url,
        createdAt = SimpleDateFormat(API_DATE_FORMAT, Locale.US).parse(response.createdAt),
        company = response.company,
        companyURL = response.companyURL,
        location = response.location,
        description = response.description,
        companyLogoURL = response.companyLogoURL,
        howToApply = response.howToApply,
        title = response.title,
        id = response.id
    )
}