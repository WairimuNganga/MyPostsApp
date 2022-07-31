package com.example.myposts

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myposts.databinding.PostListItemBinding

class PostsRvAdapter(var context: Context, var postsList:List<Post>): RecyclerView.Adapter<PostViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        var binding = PostListItemBinding.inflate(LayoutInflater.from(context),parent,
        false)
        return PostViewHolder(binding)

    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        var currentPost = postsList.get(position)
        with(holder.binding){
            tvFetchedId.text = currentPost.id.toString()
            tvFetchedPost.text = currentPost.body.toString()
            tvFetchedTitle.text = currentPost.title.toString()
            tvFetchedUserId.text = currentPost.userId.toString()
        }
    }

    override fun getItemCount(): Int {
        return postsList.size
    }

}
class PostViewHolder(var binding:PostListItemBinding):RecyclerView.ViewHolder(binding.root)