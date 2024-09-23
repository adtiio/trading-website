package com.trading.service;

import org.springframework.stereotype.Service;

import com.trading.model.Asset;
import com.trading.model.Coin;
import com.trading.model.User;

@Service
public class AssetServiceImpl implements AssetService{

    @Override
    public Asset createAsset(User user, Coin coin, double quantity) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAsset'");
    }

    @Override
    public Asset getAssetById(Long assetId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAssetById'");
    }

    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAssetByUserIdAndId'");
    }

}
