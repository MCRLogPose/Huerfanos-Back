package com.bakerysystem.claims.service;

import com.bakerysystem.claims.model.Claim;
import com.bakerysystem.claims.model.ClaimImage;
import com.bakerysystem.claims.repository.ClaimImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimImageService {

    private final ClaimImageRepository claimImageRepository;

    public List<ClaimImage> saveMultiple(Claim claim, List<String> urls, String provider) {
        List<ClaimImage> images = new ArrayList<>();
        int position = 0;
        for (String url : urls) {
            images.add(ClaimImage.builder()
                    .claim(claim)
                    .url(url)
                    .provider(provider)
                    .altText("Evidencia reclamo " + claim.getId())
                    .position(position++)
                    .build());
        }
        return claimImageRepository.saveAll(images);
    }
}
