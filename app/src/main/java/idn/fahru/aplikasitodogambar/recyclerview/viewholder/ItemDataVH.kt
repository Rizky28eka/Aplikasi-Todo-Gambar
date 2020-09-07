package idn.fahru.aplikasitodogambar.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import idn.fahru.aplikasitodogambar.R
import idn.fahru.aplikasitodogambar.databinding.ItemDataBinding
import idn.fahru.aplikasitodogambar.model.ModelData

/**
 * Created by Imam Fahrur Rofi on 04/09/2020.
 */
class ItemDataVH(private val binding: ItemDataBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ModelData) {
        Glide.with(binding.root.context)
            .load(data.profile_image)
            .centerCrop()
            .placeholder(R.drawable.gambar_placeholder)
            .into(binding.imgProfile)

        binding.run {
            txtAlamat.text = data.profile_address
            txtKelas.text = data.profile_class
            txtNama.text = data.profile_name
        }
    }
}