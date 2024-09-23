package com.trading.service;

import org.springframework.stereotype.Service;

import com.trading.model.Asset;
import com.trading.model.Coin;
import com.trading.model.User;

public interface AssetService {

    Asset createAsset(User user,Coin coin, double quantity);
    Asset getAssetById(Long assetId);
    Asset getAssetByUserIdAndId(Long userId,Long assetId);
}
