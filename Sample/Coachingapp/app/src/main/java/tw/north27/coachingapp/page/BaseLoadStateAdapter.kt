package tw.north27.coachingapp.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.LifecycleOwner
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxrelay3.PublishRelay
import io.reactivex.rxjava3.disposables.CompositeDisposable
import tw.north27.coachingapp.databinding.ItemLoadStateBinding
import tw.north27.coachingapp.ext.autoBreatheAlphaAnim
import tw.north27.coachingapp.ext.viewBinding

class BaseLoadStateAdapter(
    private val owner: LifecycleOwner,
    private val compositeDisposable: CompositeDisposable
) : LoadStateAdapter<BaseLoadStateAdapter.VH>() {

    val retryClickRelay = PublishRelay.create<View>()

    inner class VH(val binding: ItemLoadStateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) = binding.apply {
            ivIcon.isVisible = loadState is LoadState.Loading
            ivIcon.autoBreatheAlphaAnim(owner, compositeDisposable)
            tvError.isVisible = loadState is LoadState.Error
            btnRetry.isVisible = loadState is LoadState.Error
            btnRetry.setOnClickListener { retryClickRelay.accept(it) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): VH {
        val binding by viewBinding<ItemLoadStateBinding, BaseLoadStateAdapter.VH>(ItemLoadStateBinding::inflate, LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, loadState: LoadState) {
        holder.bind(loadState)
    }

}