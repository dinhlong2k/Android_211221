package com.example.android_71221.Admin.AdminActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android_71221.Admin.API.Const;
import com.example.android_71221.Admin.API.ImageService;
import com.example.android_71221.Admin.Model.Image;
import com.example.android_71221.Admin.Model.Upload;
import com.example.android_71221.Admin.Util.RandomString;
import com.example.android_71221.Admin.Util.RealPathUtil;
import com.example.android_71221.DB_Helper.DB_Helper;
import com.example.android_71221.Model.Category;
import com.example.android_71221.Model.CategoryModel;
import com.example.android_71221.Model.Product;
import com.example.android_71221.Model.ProductModel;
import com.example.android_71221.R;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {
    private DB_Helper db_helper;
    private Button loadImgBtn;
    private EditText productName;
    private EditText productPrice;
    private EditText productVersion;
    private EditText productQtt;
    private EditText productDescript;
    private Spinner dropdown;
    private ImageView exitBtn;
    private ImageView loadImg_1;
    private ImageView loadImg_2;
    private ImageView loadImg_3;
    private ImageView loadImg_4;
    private Boolean clicked_1 = false;
    private Boolean clicked_2 = false;
    private Boolean clicked_3 = false;
    private Boolean clicked_4 = false;
    private ArrayList<Uri> uriArrayList = new ArrayList<Uri>();
    private ArrayList<String> imgURL = new ArrayList<String>();
    private ProgressDialog progressDialog;
    private ArrayList<Category> categoryArrayList;
    private CategoryModel categoryModel;
    private Product product;
    private ProductModel productModel;
    private StorageReference storageReference;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        reference();

        populateSpinner();

        loadImgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOpenBottomSheetDialog();
            }
        });

        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    private void populateSpinner(){
        categoryArrayList = categoryModel.DisplayCategory();
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0 ; i< categoryArrayList.size();i++){
            arrayList.add(categoryArrayList.get(i).getName());
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_dropdown_item, arrayList);
        dropdown.setAdapter(spinnerArrayAdapter);
    }

    private void clickOpenBottomSheetDialog() {
        View viewDialog = getLayoutInflater().inflate(R.layout.load_image_layout, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(viewDialog);

        bottomSheetDialog.show();

        Button cancelBtn = viewDialog.findViewById(R.id.cancelBtn);
        Button addBtn = viewDialog.findViewById(R.id.addBtn);

        loadImg_1 = viewDialog.findViewById(R.id.img_load);
        loadImg_2 = viewDialog.findViewById(R.id.img_load_1);
        loadImg_3 = viewDialog.findViewById(R.id.img_load_2);
        loadImg_4 = viewDialog.findViewById(R.id.img_load_3);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uriArrayList.clear();
                imgURL.clear();
                bottomSheetDialog.dismiss();
            }
        });

        loadImg_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
                clicked_1 = true;
                clicked_2 = false;
                clicked_3 = false;
                clicked_4 = false;

            }
        });

        loadImg_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
                clicked_1 = false;
                clicked_2 = true;
                clicked_3 = false;
                clicked_4 = false;
            }
        });

        loadImg_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
                clicked_1 = false;
                clicked_2 = false;
                clicked_3 = true;
                clicked_4 = false;
            }
        });

        loadImg_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
                clicked_1 = false;
                clicked_2 = false;
                clicked_3 = false;
                clicked_4 = true;
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (uriArrayList.size() > 0){
                    for (int i = 0; i < uriArrayList.size();i++){
                        Uri uri = uriArrayList.get(i);
                        uploadFile(uri);
                    }
                    bottomSheetDialog.dismiss();
                }
            }
        });


    }

    private void getDataFromEditText(){
        String name = productName.getText().toString().trim();
        String price = productPrice.getText().toString().trim();
        String version = productVersion.getText().toString().trim();
        String qtt = productQtt.getText().toString().trim();
        String descript = productDescript.getText().toString().trim();
        int type = 1;
        type = dropdown.getSelectedItemPosition() + 1;
        product = new Product(-1, name, version, Double.parseDouble(price), Integer.parseInt(qtt), descript, imgURL, type);
    }



    private void reference(){
        loadImgBtn = findViewById(R.id.loadImgBtn);
        exitBtn = findViewById(R.id.exitBtn);
        progressDialog = new ProgressDialog(this);
        dropdown = findViewById(R.id.spinnerType);
        progressDialog.setMessage("Waiting for uploading data...");

        productName = findViewById(R.id.editText_productName);
        productPrice = findViewById(R.id.editText_productPrice);
        productDescript = findViewById(R.id.editText_productDescript);
        productVersion = findViewById(R.id.editText_productVersion);
        productQtt = findViewById(R.id.product_qtt_edt);

        //set up storage
        storageReference = FirebaseStorage.getInstance().getReference("uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        db_helper = new DB_Helper(this, "EmarketDB.sqlite", null, 1);
        categoryModel = new CategoryModel(db_helper);
        categoryArrayList = new ArrayList<Category>();

        productModel = new ProductModel(db_helper);
        productModel.CreateTableProduct();
        productModel.CreateTableImages();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            if (clicked_1){
                loadImg_1.setImageURI(selectedImage);
                uriArrayList.add(selectedImage);
            }

            else if (clicked_2){
                loadImg_2.setImageURI(selectedImage);
                uriArrayList.add(selectedImage);
            }
            else if (clicked_3){
                loadImg_3.setImageURI(selectedImage);
                uriArrayList.add(selectedImage);
            }
            else{
                loadImg_4.setImageURI(selectedImage);
                uriArrayList.add(selectedImage);
            }
        }
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadFile(Uri uri) {
        StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "."+getFileExtension(uri));
        fileReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                            }
                        }, 5000);

                        Toast.makeText(AddProductActivity.this, "Upload ảnh thành công", Toast.LENGTH_SHORT).show();
                        String imageName = getAlphaNumericString(8);

                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                //got URI
                                Upload upload = new Upload(imageName, uri.toString());

                                //// Wrap with Uri.parse() when retrieving
                                String uploadID = databaseReference.push().getKey();
                                databaseReference.child(uploadID).setValue(upload);
                                Log.d("onSuccess", "onSuccess: "+upload.getmImageURL());


                                imgURL.add(upload.getmImageURL());
                                if (imgURL.size() == uriArrayList.size()){
                                    getDataFromEditText();
                                    productModel.InsertProduct(product);
                                    productModel.InsertImg(product);
                                    progressDialog.dismiss();
                                    finish();

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                        progressDialog.dismiss();


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddProductActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
//                        double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
//                        progressBar.setProgress((int) progress);
                        progressDialog.show();

                    }
                });
    }

    public String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}