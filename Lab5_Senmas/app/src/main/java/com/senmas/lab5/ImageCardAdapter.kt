import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.senmas.lab5.R
import com.squareup.picasso.Picasso

class ImageCardAdapter(val images: Array<String>) : RecyclerView.Adapter<ImageCardAdapter.ImageCardViewHolder>() {
    class ImageCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val viewGroup = view as ViewGroup
        private val imageView = viewGroup.getChildAt(0) as ImageView

        fun downloadImage(url: String) {
            Picasso.get().load(url).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image, parent, false)
        return ImageCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageCardViewHolder, position: Int) {
        val url = images[position]
        holder.downloadImage(url)
    }

    override fun getItemCount(): Int = images.size
}