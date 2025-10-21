package com.bakerysystem;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryTest {
    public static void main(String[] args) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "diwnoddb2",
                "api_key", "626888795535982",
                "api_secret", "qo_9dAbUqNfQ2Ed3bssZiHPZ1q4"
        ));

        try {
            var result = cloudinary.uploader().upload("https://img.freepik.com/vector-premium/diseno-logotipo-letra-ima-empresa-tecnologia-diseno-logotipo-ima-combinacion-colores-blanco-negro-ima-logo-ima-vector-ima-diseno-ima-icono-ima-alfabeto-ima-tipografia-diseno-logotipo_229120-152744.jpg", ObjectUtils.emptyMap());
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
