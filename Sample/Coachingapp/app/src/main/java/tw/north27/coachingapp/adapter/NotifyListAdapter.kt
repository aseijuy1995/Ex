//package tw.north27.coachingapp.adapter
//
//import android.view.ViewGroup
//import androidx.paging.PagingDataAdapter
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.RecyclerView
//import androidx.viewbinding.ViewBinding
//import tw.north27.coachingapp.model.Notify
//
//class NotifyListAdapter : PagingDataAdapter<Notify, NotifyListAdapter.VH>(
//    object : DiffUtil.ItemCallback<Notify>() {
//        override fun areItemsTheSame(oldItem: Notify, newItem: Notify): Boolean {
//            return oldItem.hashCode() == newItem.hashCode()
//        }
//
//        override fun areContentsTheSame(oldItem: Notify, newItem: Notify): Boolean {
//            return oldItem.hashCode() == newItem.hashCode()
//        }
//
//    }
//) {
//
//    inner class VH(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
//
//    }
//
//    override fun onBindViewHolder(holder: VH, position: Int) {
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//}