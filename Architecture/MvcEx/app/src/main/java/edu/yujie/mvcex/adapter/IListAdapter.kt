package edu.yujie.mvcex.adapter

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import edu.yujie.mvcex.model.ItemXXXX

abstract class IListAdapter<T,VH:RecyclerView.ViewHolder>(config: DiffUtil.ItemCallback<T>) : ListAdapter<T, VH>(config)