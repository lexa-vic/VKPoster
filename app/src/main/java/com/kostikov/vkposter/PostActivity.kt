package com.kostikov.vkposter

import android.Manifest
import android.graphics.*
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.Spanned
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.kostikov.vkposter.backgroundchoose.Beach
import com.kostikov.vkposter.backgroundchoose.Color
import com.kostikov.vkposter.backgroundchoose.Image
import com.kostikov.vkposter.backgroundchoose.Stars
import com.kostikov.vkposter.backgroundchoose.adapter.BackgroundAdapter
import com.kostikov.vkposter.backgroundchoose.adapter.BackgroundSelect
import com.kostikov.vkposter.backgroundchoose.adapter.backgroundData
import com.kostikov.vkposter.backgroundchoose.layoutmanager.CenterLinearLayoutManager
import com.kostikov.vkposter.savedata.FileSaveService
import com.kostikov.vkposter.savedata.FileSaveService.Companion.FOLDER_NAME
import com.kostikov.vkposter.stickers.MotionView
import com.kostikov.vkposter.stickers.StickerListDialogFragment
import com.kostikov.vkposter.stickers.entity.ImageEntity
import com.kostikov.vkposter.stickers.entity.MotionEntity
import com.kostikov.vkposter.stickers.viewmodel.Layer
import com.kostikov.vkposter.textstyle.RoundBackgroundSpan
import com.kostikov.vkposter.textstyle.textStyleList
import com.mlsdev.rximagepicker.RxImagePicker
import com.mlsdev.rximagepicker.Sources
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_post.*
import java.text.SimpleDateFormat
import java.util.*


class PostActivity : AppCompatActivity(), StickerListDialogFragment.Listener {

    private var textStyleIdx = 0;
    private lateinit var textSpan: RoundBackgroundSpan

    private val compositeDisposable = CompositeDisposable()

    private lateinit var fileSaveService: FileSaveService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        fileSaveService = (application as AppApplication).getFileSaveService()

        initBottomBackgroundChooseWindow()
        initPostEditText()
        initStickers()
        initSaveButton()
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }

    private fun closeKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun initSaveButton() {
        savePostButton.setOnClickListener{
            RxPermissions(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .filter { it }
                .subscribe({
                    createPostBitmap()
                }, {
                    it.printStackTrace()
                })
        }
    }

    private fun initStickers() {
        addStickerImg.setOnClickListener {

            closeKeyboard()
            StickerListDialogFragment.newInstance(24)
                .show(supportFragmentManager, null)
        }

        stickerMotionView.setMotionViewCallback(motionViewCallback)
    }

    override fun onStickerClicked(stickerId: Int) {
        val layer = Layer()
        val pica = BitmapFactory.decodeResource(resources, stickerId)

        val entity = ImageEntity(layer, pica, stickerMotionView.getWidth(), stickerMotionView.getHeight())

        stickerMotionView.addEntityAndPosition(entity)
        entity.moveCenterTo(PointF(stickerMotionView.getWidth() / 4f, stickerMotionView.getHeight() / 4f))
    }

    private fun initPostEditText() {
        setTextStyle(textStyleIdx)

        postEditText.setShadowLayer(resources.getDimension(R.dimen.text_background_corner_radius), 0f, 0f, 0)

        postEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val len = s?.length ?: 0
                s?.length
                s?.setSpan(textSpan,0, len,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        changeTextStyleImg.setOnClickListener {
            textStyleIdx++

            if (textStyleIdx >= textStyleList.size) {
                textStyleIdx = 0
            }

            setTextStyle(textStyleIdx)
        }
    }

    private fun createPostBitmap() {

        Log.d("PostActivity", "start save")
        closeKeyboard()
        postEditText.isCursorVisible = false

        val bitmap = Bitmap.createBitmap(
            postBackgroundHolder.width,
            postBackgroundHolder.height,
            Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        postBackgroundHolder.draw(canvas)
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, 1080, 1080, true)
        bitmap.recycle()

        // Create a media file name
        val timeStamp = SimpleDateFormat("MMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageName = "VkPost_$timeStamp.jpg"
        val saveInstanceStr = " .../$FOLDER_NAME/$imageName"
        compositeDisposable.add(
            fileSaveService.storePost(scaledBitmap, imageName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Toast.makeText(this, getString(R.string.save_success) + saveInstanceStr, Toast.LENGTH_LONG).show()
            }, {
                Toast.makeText(this, getString(R.string.save_fail), Toast.LENGTH_LONG).show()
            }))
        postEditText.isCursorVisible = true
    }


    private fun setTextStyle(styleListIdx: Int) {
        postEditText.setTextColor(resources.getColor(textStyleList[styleListIdx].textColor))

        textSpan = RoundBackgroundSpan(resources.getColor(textStyleList[styleListIdx].backgroundColor), resources.getDimension(R.dimen.post_edit_text_padding), resources.getDimension(R.dimen.text_background_corner_radius))
        val str = postEditText.editableText.toString()

        postEditText.editableText.clear()
        postEditText.editableText.append(str)
    }

    private fun initBottomBackgroundChooseWindow() {

        postBackgroundRecyclerView.apply {
            layoutManager = CenterLinearLayoutManager(this@PostActivity).apply { orientation = HORIZONTAL }
            adapter = BackgroundAdapter {
                onSelectBackgroundTypeHandle(it)
            }
        }
    }

    private fun onSelectBackgroundTypeHandle(position: Int) {
        val background = backgroundData[position]

        postTrashBasket.setBackgroundResource(R.drawable.drawable_bin)

        when(background) {
            is Color -> {
                setHeaderAndFooterVisibility(View.GONE)

                postBackgroundImage.setImageDrawable(resources.getDrawable(background.colorDrawableResId!!))

                if (background.colorDrawableResId == R.drawable.background_white_full) {
                    postTrashBasket.setBackgroundResource(R.drawable.drawable_bin_circle)
                }
            }
            is Beach -> {
                setHeaderAndFooterVisibility(View.VISIBLE)

                postBackgroundImage.setImageDrawable(null)
                postBackgroundImage.setBackgroundResource(background.bodyDrawableResId!!)
            }
            is Stars -> {
                setHeaderAndFooterVisibility(View.GONE)

                postBackgroundImage.setImageDrawable(null)
                postBackgroundImage.setBackgroundResource(background.bodyDrawableResId!!)
            }
            is Image -> {
                setHeaderAndFooterVisibility(View.GONE)

                RxPermissions(this)
                    .request(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .filter { it }
                    .flatMap { RxImagePicker.with(this).requestImage(Sources.GALLERY) }
                    .subscribe({
                        setBackgroundFromUri(it)
                    }, {
                        it.printStackTrace()
                    })

            }
        }

        (postBackgroundRecyclerView.adapter as BackgroundSelect).setSelectedItem(position)
    }

    private fun setBackgroundFromUri(uri: Uri) {
        val screenSize = Point()
        windowManager.defaultDisplay.getSize(screenSize)

        Glide.with(this)
            .load(uri)
            .apply(RequestOptions.centerCropTransform())
            .into(postBackgroundImage)
    }

    private fun setHeaderAndFooterVisibility(visibility: Int){

        postTopBackgroundImage.visibility = visibility
        postBottomBackgroundImage.visibility = visibility
    }


    private val motionViewCallback = object : MotionView.MotionViewCallback {

        private var isOnBasket = false

        override fun onEntitySelected(entity: MotionEntity?) {
            if  (entity != null) {
                postTrashBasket.translationY = 300f

                postTrashBasket.animate()
                    .translationYBy(-300f)
                    .alpha(1f)
                    .setDuration(300)

            } else {
                postTrashBasket.animate()
                    .translationYBy(300f)
                    .alpha(0f)
                    .setDuration(300)
            }
        }

        override fun onEntityDoubleTap(entity: MotionEntity) {

        }

        override fun onEntityTouch(entity: MotionEntity?, event: MotionEvent) {
            val currX = event.x
            val currY = event.y
            val x0 = postTrashBasket.getX()
            val x1 = postTrashBasket.getX() + postTrashBasket.getWidth()
            val y0 = postTrashBasket.getY()
            val y1 = postTrashBasket.getY() + postTrashBasket.getHeight()

            if (currX >= x0 && currX <= x1 &&
                currY >= y0 && currY <= y1) {

                if (!isOnBasket) {
                    postTrashBasket.setImageResource(R.drawable.ic_fab_trash_released)

                    postTrashBasket.animate()
                        .scaleX(1.2f)
                        .scaleY(1.2f)
                        .setDuration(100)
                }

                isOnBasket = true

                if (event.action == MotionEvent.ACTION_UP) {
                    stickerMotionView.deletedSelectedEntity()

                    postTrashBasket.animate()
                        .translationYBy(300f)
                        .alpha(0f)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(300)
                }
            } else {
                if (isOnBasket) {
                    postTrashBasket.setImageResource(R.drawable.ic_fab_trash)
                    postTrashBasket.animate()
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(100)
                }

                isOnBasket = false
            }
        }
    }
}
