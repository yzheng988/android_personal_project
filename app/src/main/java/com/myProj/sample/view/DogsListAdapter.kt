package com.myProj.sample.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.myProj.sample.R
import com.myProj.sample.databinding.ItemDogBinding
import com.myProj.sample.model.DogBreed
import com.myProj.sample.utils.getProgressDrawable
import com.myProj.sample.utils.loadimage
import kotlinx.android.synthetic.main.item_dog.view.*

class DogsListAdapter(val dogList: ArrayList<DogBreed>) :
    RecyclerView.Adapter<DogsListAdapter.DogViewHolder>(), DogClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val inflater = LayoutInflater.from(parent.context)
//        val view = inflater.inflate(R.layout.item_dog, parent, false)
        val view =
            DataBindingUtil.inflate<ItemDogBinding>(inflater, R.layout.item_dog, parent, false)
        return DogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dogList.size
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        // bind dog to the dog variable in the xml
        holder.view.dog = dogList[position]
        holder.view.listener = this
//        holder.view.name.text = dogList[position].dogBreed
//        holder.view.lifespan.text = dogList[position].lifeSpan
//        holder.view.setOnClickListener {
//            val action = ListFragmentDirections.actionListFragmentToDetailFragment()
//            action.dogUUID = dogList[position].uuid
//            Log.d("dogUUID", "uuid sent: ${action.dogUUID}")
//            Navigation.findNavController(it).navigate(action)
//        }
//        holder.view.imageView.loadimage(dogList[position].imgUrl, getProgressDrawable(holder.view.imageView.context))
    }

    fun updateDogList(newDogList: List<DogBreed>) {
        dogList.clear()
        dogList.addAll(newDogList)
        // recreate the data
        notifyDataSetChanged()
    }

    class DogViewHolder(val view: ItemDogBinding) : RecyclerView.ViewHolder(view.root)

    override fun onDogClicked(v: View) {
        val uuid = v.dogId.text.toString().toInt()
        val action = ListFragmentDirections.actionListFragmentToDetailFragment()
        action.dogUUID = uuid
        Log.d("dogUUID", "uuid sent: ${action.dogUUID}")
        Navigation.findNavController(v).navigate(action)
    }
}