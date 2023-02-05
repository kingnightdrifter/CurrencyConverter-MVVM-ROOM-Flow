package com.street.paypay_currencyconverter.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.street.paypay_currencyconverter.domain.domain_models.ExchangeRatesDomainModel
import com.street.paypay_currencyconverter.databinding.ItemCurrencyQuotesBinding
import javax.inject.Inject

class ExchangeRatesAdapter @Inject constructor() :
    RecyclerView.Adapter<ExchangeRatesAdapter.RatesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        val binding = ItemCurrencyQuotesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RatesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        holder.bind(differ.currentList[position], position)
    }

    override fun getItemCount() = differ.currentList.size


    inner class RatesViewHolder(private val itemBinding: ItemCurrencyQuotesBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(model: ExchangeRatesDomainModel, position: Int) {
            itemBinding.apply {
                data = model
            }
        }
    }

    private val differCallBack  = object : DiffUtil.ItemCallback<ExchangeRatesDomainModel, >() {

        override fun areItemsTheSame(oldItem: ExchangeRatesDomainModel, newItem: ExchangeRatesDomainModel, ): Boolean {
            return  oldItem.currencyName == newItem.currencyName
        }
        override fun areContentsTheSame(oldItem: ExchangeRatesDomainModel, newItem: ExchangeRatesDomainModel, ): Boolean {
            return  oldItem==newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallBack)
}
