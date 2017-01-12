package com.knowledge.health.widget.selectphoto;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.knowledge.health.HealthApplication;
import com.knowledge.health.util.BitmapHelper;
import com.knowledge.health.util.CommonUtil;
import com.soundcloud.android.crop.Crop;

import java.io.File;

/**
 * Created by RudyJun on 2017/1/11.
 */

public class TakePhoto {

    private Uri resultUri;
    private int requestCamera;
    private int requestSelectFile;
    private int requestCropImage;
    private Object context;
    private Activity activity;
    private TakePhotoCallback callback;
    private Uri uri;

    private File tempImage;

    public static class Builder {

        private Activity activity;
        private Object context;
        private TakePhotoCallback callback;
        //保存图片目录地址
        private Uri uri;

        /**
         * 传入当前页面的context
         *
         * @param object
         * @return
         */
        public Builder init(Activity object) {
            this.context = object;
            return this;
        }

        /**
         * 传入当前页面的context
         *
         * @param object
         * @return
         */
        public Builder init(Fragment object) {
            this.context = object;
            return this;
        }

        public Builder init(android.support.v4.app.Fragment object) {
            this.context = object;
            return this;
        }

        /**
         * 传入回调方法
         *
         * @param callback
         * @return
         */
        public Builder callback(TakePhotoCallback callback) {
            this.callback = callback;
            return this;
        }

        /**
         * 最终图片的保存地址
         *
         * @param uri
         * @return
         */
        public Builder uri(Uri uri) {
            this.uri = uri;
            return this;
        }

        /**
         * 最终图片的保存地址
         *
         * @param uri
         * @return
         */
        public Builder uri(File uri) {
            this.uri = Uri.fromFile(uri);
            return this;
        }

        /**
         * 最终图片的保存地址
         *
         * @param uri
         * @return
         */
        public Builder uri(String uri) {
            this.uri = Uri.fromFile(new File(uri));
            return this;
        }

        /**
         * 根据builder生成TakeImage
         *
         * @return
         */
        public TakePhoto build() {
            initEmptyField();
            return new TakePhoto(this);
        }

        private void initEmptyField() {

            if (context == null) {
                throw new IllegalArgumentException("context is null");
            }

            if (context instanceof Activity) {
                activity = (Activity) context;
            }
            if (context instanceof Fragment) {
                activity = ((Fragment) context).getActivity();
            }
            if (context instanceof android.support.v4.app.Fragment) {
                activity = ((android.support.v4.app.Fragment) context).getActivity();
            }

            if (uri == null) {
                throw new IllegalArgumentException("uri is null");
            }

            if (new File(uri.getPath()).isFile()) {
                throw new IllegalArgumentException("uri is not directory");
            }

            if (callback == null) {
                callback = new TakePhotoCallback() {
                    @Override
                    public void callback(Uri resultUri) {
                        Log.e("TakePhoto", "TakePhotoCallback should be achieved");
                    }
                };
            }
        }
    }

    private TakePhoto(Builder builder) {
        requestCamera = Crop.REQUEST_CAMERA;
        requestSelectFile = Crop.REQUEST_PICK;
        requestCropImage = Crop.REQUEST_CROP;
        context = builder.context;
        activity = builder.activity;
        callback = builder.callback;
        uri = builder.uri;
        tempImage = new File(HealthApplication.getApplication().getAppCacheDir() + "/temp.jpg");
    }

    public void initTakePhoto() {

        final CharSequence[] items = {"拍照",
                "从手机相册中挑选", "取消"};

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (0 == item) {
                    Uri imageUri = Uri.fromFile(tempImage);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    if (context instanceof Activity) {
                        ((Activity) context).startActivityForResult(intent, requestCamera);
                    }
                    if (context instanceof Fragment) {
                        ((Fragment) context).startActivityForResult(intent, requestCamera);
                    }
                    if (context instanceof android.support.v4.app.Fragment) {
                        ((android.support.v4.app.Fragment) context).startActivityForResult(intent, requestCamera);
                    }
                } else if (1 == item) {
                    Crop.pickImage(activity);
                } else if (2 == item) {
                    dialog.dismiss();
                }
            }
        });

        builder.create().show();
    }

    /**
     * activity的回调方法，在activity或者fragment的onActivityResult中调用
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == requestCamera) {
                generateFileUri();

                if (null == tempImage || !tempImage.exists()) {
                    CommonUtil.showToast(activity, "图片不存在");
                    return;
                }

                // 检查是否图片翻转
                BitmapHelper.checkAndRotatePic(tempImage.getAbsolutePath());

                cropImageUri(Uri.fromFile(tempImage), resultUri);

            } else if (requestCode == requestSelectFile) {
                generateFileUri();
                cropImageUri(data.getData(), resultUri);
            } else if (requestCode == requestCropImage) {
                resultUri = Crop.getOutput(data);
                callback.callback(resultUri);
            }
        }
    }

    private void generateFileUri() {
        File resultFile = new File(uri.getPath() + "/" + System.currentTimeMillis() + ".jpg");
        if (!resultFile.getParentFile().exists()) {
            resultFile.getParentFile().mkdirs();
        }
        resultUri = Uri.fromFile(resultFile);
    }

    private void cropImageUri(Uri imageUri, Uri resultUri) {
        //圆形裁剪
        Crop.of(imageUri, resultUri).asCircle().start(activity);
    }
}
