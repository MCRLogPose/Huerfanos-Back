package com.bakerysystem.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

        @Bean
        public Cloudinary cloudinary() {
            return new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "diwnoddb2",
                    "api_key", "626888795535982",
                    "api_secret", "qo_9dAbUqNfQ2Ed3bssZiHPZ1q4"
            ));
        }
    }
