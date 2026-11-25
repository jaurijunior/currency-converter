package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.network.KtorClient
import com.example.currencyconverter.domain.model.CurrencyConversion
import com.example.currencyconverter.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val ktorClient: KtorClient,
) : CurrencyRepository {

    override suspend fun convertCurrency(
        fromCurrency: String,
        toCurrency: String,
        amount: Double
    ): Result<CurrencyConversion> {
        return runCatching {
            val response = ktorClient.convertCurrency(
                fromCurrency = fromCurrency,
                toCurrency = toCurrency,
                amount = amount
            )

            CurrencyConversion(
                baseCode = response.baseCode,
                targetCode = response.targetCode,
                conversionRate = response.conversionRate.toString(),
                conversionResult = response.conversionResult.toString()
            )
        }
    }
}