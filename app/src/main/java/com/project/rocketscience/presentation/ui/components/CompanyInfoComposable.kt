package com.project.rocketscience.presentation.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.project.rocketscience.R
import com.project.rocketscience.data.remote.model.CompanyInfo

/**
 * A composable that displays basic company information.
 *
 * @param companyInfo The [CompanyInfo] object containing the data to display.
 *
 * This component shows:
 * - A static title ("Company")
 * - A description retrieved from [companyInfo.getCompanyDescription()]
 */

@Composable
fun CompanyInfoContent(companyInfo: CompanyInfo) {
    Text(
        text = stringResource(id = R.string.company_title),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start
    )
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = companyInfo.getCompanyDescription(),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.fillMaxWidth()
    )
}
