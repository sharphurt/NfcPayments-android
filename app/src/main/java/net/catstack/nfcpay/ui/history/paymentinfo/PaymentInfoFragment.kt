package net.catstack.nfcpay.ui.history.paymentinfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.payment_info_fragment.*
import net.catstack.nfcpay.MainActivity
import net.catstack.nfcpay.R
import net.catstack.nfcpay.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

class PaymentInfoFragment : BaseFragment(true, R.color.background) {
    // TODO: Add PaymentInfoViewModel to DI
    private val viewModel: PaymentInfoViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.payment_info_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (requireActivity() as MainActivity).hideBottomNavigation()

        val historyItem = PaymentInfoFragmentArgs.fromBundle(requireArguments()).historyItemModel

        val firstCost = DecimalFormat("###,###", DecimalFormatSymbols().apply {
            groupingSeparator = ' '
        }).format(historyItem.cost)
        val secondCost =
            (((historyItem.cost * 100) - historyItem.cost.toLong() * 100)).toLong().toString()
                .padEnd(2, '0')
        costText.text = resources.getString(R.string.home_balance, firstCost, secondCost)

        cardText.text = historyItem.title
        dateText.text = historyItem.paymentDateAndTime
        paymentTypeText.text = resources.getString(R.string.history_with_nfc)

        val title = historyItem.title.toLowerCase()
        when {
            title.startsWith("mir") -> {
                cardTypeImage.setImageResource(R.drawable.ic_mir)
            }
            title.startsWith("visa") -> {
                cardTypeImage.setImageResource(R.drawable.ic_visa)
            }
            title.startsWith("mastercard") -> {
                cardTypeImage.setImageResource(R.drawable.ic_mastercard)
            }
        }

        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroy() {
        (requireActivity() as MainActivity).showBottomNavigation()
        super.onDestroy()
    }
}