package com.example.darnamob.Database.data

import android.view.View
import androidx.recyclerview.widget.RecyclerView

data class Comment(val commenterID: Int,
                   val artisanId: Int,
                   val commentText: String,
                    val rating: Float) {
    class ViewHolder(comment_recycler_row: View): RecyclerView.ViewHolder(comment_recycler_row) {

    }
}
