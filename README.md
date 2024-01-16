Download & Save Image
Android Studio `Get Form Version Control`
```
https://github.com/AtikulSoftware/ListViewItemSave-As-Image.git
```

Terminal cmd:
```
https://github.com/AtikulSoftware/ListViewItemSave-As-Image.git
```
১। Image & Layout সহজেই save করতে পারবেন png Format <br>
২। Android Version 13+ এর মধ্যেও কাজ করবে । <br> <br> 

> LinearLayout কে কিভাবে Bitmap করবেন ?
```
linearLayout.setDrawingCacheEnabled(true);
Bitmap bitmap = Bitmap.createBitmap(linearLayout.getDrawingCache());
linearLayout.setDrawingCacheEnabled(false);
```
> [!NOTE]
> `অবশ্যই আগে LinearLayout কে findViewById দিয়ে পরিচয় করিয়ে দিবেন`

<br> 
<br> 

> কিভাবে ImageView Bitmap এ convert করবেন ? 
```
 Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
```
> [!NOTE]
> `অবশ্যই আগে ImageView কে findViewById দিয়ে পরিচয় করিয়ে দিবেন`

<br> 

<table align="center">
    <tr>
        <th>Home Page</th>
        <th>Saved Image</th>
    </tr>
    <tr>
        <td><img width="200"
                src="https://raw.githubusercontent.com/AtikulSoftware/AtikulFiles/main/image_save/home_page.png"
                alt="Home Page">
        </td>
        <td><img width="200"
                src="https://raw.githubusercontent.com/AtikulSoftware/AtikulFiles/main/image_save/saved_image.png"
                alt="Saved Image">
        </td>
    </tr>
</table>

<h1 align="center">Thank You ❤️</h1>


