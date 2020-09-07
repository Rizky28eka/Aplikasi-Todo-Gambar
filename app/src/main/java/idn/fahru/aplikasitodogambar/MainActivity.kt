package idn.fahru.aplikasitodogambar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import idn.fahru.aplikasitodogambar.databinding.ActivityMainBinding
import idn.fahru.aplikasitodogambar.model.ModelData
import idn.fahru.aplikasitodogambar.recyclerview.adapter.ItemDataAdapter
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {

    //sehingga kita perlu memngisi variabel binding, adapterMain, dabataseUser, valueEventListener di OnCreate
    private lateinit var binding: ActivityMainBinding

    private lateinit var databaseUser : DatabaseReference

    // buat variabel adapter untuk recyclerview
    private lateinit var adapterMain: ItemDataAdapter

    private lateinit var valueEventListener: ValueEventListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //isi variabel binding
        val inflater:LayoutInflater = layoutInflater
        binding =  ActivityMainBinding.inflate(inflater)
        setContentView(binding.root)

        adapterMain = ItemDataAdapter()

        databaseUser = FirebaseDatabase.getInstance().reference.child("Users")

        binding.extendedFab.setOnClickListener {
            val intent = Intent(this, AddDataActivity::class.java)
            startActivity(intent)
        }

        // setting RecyclerView
        binding.rvMain.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterMain
            setHasFixedSize(true)
        }

        //buat valueEventListener untuk mengecek data yang ada di firebase
        valueEventListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.childrenCount > 0) {
                    //buat array kosong dari model data
                    val daftarUser = arrayListOf<ModelData>()
                    for(dataUser in snapshot.children) {
                        val data = dataUser.getValue(ModelData::class.java) as ModelData
                        daftarUser.add(data)
                    }

                    //masukkan data yang telah didapatkan ke dalam adapter recyclerview
                    adapterMain.addData(daftarUser)
                    //beritahu adapter recyclerview jika ada perubahan data
                    adapterMain.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        }

        //tambahkan valueEventListener ke dalam databaseUser
        databaseUser.addValueEventListener(valueEventListener)

    }

    override fun onDestroy() {
        super.onDestroy()
        // ini jangan dihapus.. setiap kali kita menambahkan eventlistener
        // maka perlu dihapus dengan cara removeEventListener
        // jika penambahan terjadi di oncreate
        // maka hapusnya itu ada di onDestroy seperti kode di bawah ini
        databaseUser.removeEventListener(valueEventListener)
    }
}