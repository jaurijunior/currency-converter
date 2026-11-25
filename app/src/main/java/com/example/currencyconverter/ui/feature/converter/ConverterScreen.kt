package com.example.currencyconverter.ui.feature.converter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconverter.R
import com.example.currencyconverter.ui.components.CurrencyField
import com.example.currencyconverter.ui.feature.converter.ConverterViewModel.ConversionState
import com.example.currencyconverter.ui.feature.converter.model.ConverterFormEvent
import com.example.currencyconverter.ui.feature.converter.model.ConverterFormState
import com.example.currencyconverter.ui.theme.CurrencyConverterTheme


@Composable
fun ConverterScreen(modifier: Modifier = Modifier) {
    val viewModel = viewModel<ConverterViewModel>()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    val conversionState by viewModel.conversionState.collectAsStateWithLifecycle()

    ConverterContent(
        formState = formState,
        conversionState = conversionState,
        onFormEvent = viewModel::onFormEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConverterContent(
    modifier: Modifier = Modifier,
    formState: ConverterFormState,
    conversionState: ConversionState,
    onFormEvent: (ConverterFormEvent) -> Unit
) {
    Scaffold(
        modifier = modifier
            .imePadding(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.currency_converter),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .consumeWindowInsets(innerPadding)
                .systemBarsPadding()
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column() {
                    CurrencyField(
                        currencies = formState.fromCurrenciesList,
                        selectedCurrency = formState.fromCurrencySelected,
                        currencyAmount = formState.fromCurrencyAmount,
                        onCurrencySelected = {
                            onFormEvent(ConverterFormEvent.OnFromCurrencySelected(it))
                        },
                        onCurrencyAmountChanged = {
                            onFormEvent(ConverterFormEvent.OnFromCurrencyAmountChanged(it))
                        }
                    )

                    CurrencyField(
                        currencies = formState.toCurrenciesList,
                        selectedCurrency = formState.toCurrencySelected,
                        currencyAmount = formState.toCurrencyAmount,
                        onCurrencySelected = {
                            onFormEvent(ConverterFormEvent.OnToCurrencySelected(it))
                        },
                        onCurrencyAmountChanged = { TODO() },
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                }

                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    border = BorderStroke(
                        width = 0.5.dp,
                        color = Color.LightGray
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_downward_alt),
                        contentDescription = stringResource(R.string.arrow_downward_alt),
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                contentAlignment = Alignment.Center
            ) {
                when (conversionState) {
                    ConversionState.Idle -> {
                        // No UI
                    }

                    ConversionState.Loading -> {
                        CircularProgressIndicator()
                    }

                    ConversionState.Success -> {
                        Text(
                            text = stringResource(R.string.conversion_success),
                            color = Color.Green
                        )
                    }

                    is ConversionState.Error -> {
                        Text(
                            text = conversionState.message,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Box(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    onFormEvent(ConverterFormEvent.SendConverterForm)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.currency_converter_button_text),
                )
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun ConverterContentPreview() {
    CurrencyConverterTheme {
        ConverterContent(
            formState = ConverterFormState(
                fromCurrenciesList = listOf("BRL", "USD", "EUR"),
                toCurrenciesList = listOf("USD", "BRL", "EUR"),
                fromCurrencySelected = "BRL",
                toCurrencySelected = "USD",
            ),
            conversionState = ConversionState.Idle,
            onFormEvent = { }
        )
    }
}