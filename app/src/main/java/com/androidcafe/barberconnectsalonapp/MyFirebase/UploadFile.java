package com.androidcafe.barberconnectsalonapp.MyFirebase;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadFile {

    Context context;
    StorageReference storageReference;
    OnImageUpload onImageUpload;

    public UploadFile(Context context, OnImageUpload onImageUpload) {
        this.context = context;
        this.onImageUpload = onImageUpload;
    }






    public void uploadonfirestorage(Uri saveUri, String folder, String name)
    {


        storageReference = FirebaseStorage.getInstance().getReference(folder).child(name);
        UploadTask uploadTask = storageReference.putFile(saveUri);

        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {


                double progress = (100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount();
                //finish.setText(""+(int)progress+"%");
                Toast.makeText(context, ""+progress+" %", Toast.LENGTH_SHORT).show();
                Log.e("kitnahua","onProgress : file "+progress+" % uploaded");



            }
        }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete( Task<UploadTask.TaskSnapshot> task) {
                //test.dismiss();
                Toast.makeText(context, "Successfully uploaded", Toast.LENGTH_SHORT).show();
            }
        });




        Task<Uri> task = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {

                    // Toast.makeText(Send_notice.this, "Failed", Toast.LENGTH_SHORT).show();
                    onImageUpload.getUrl(task.getException().getMessage());

                }

                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(Task<Uri> task) {


                if (task.isSuccessful()) {
                    String url = task.getResult().toString();
                    Log.d("PROFILE_DIRECTLINK", url);


                    onImageUpload.getUrl(url);


                }

            }
        });


    }





    public interface OnImageUpload
    {
        public void getUrl(String url);
    }

}
