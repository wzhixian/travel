package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    /**
     * 判断用户是否收藏
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(int uid, String rid) {
        Favorite favorite = favoriteDao.findByUidAndRid(uid, Integer.parseInt(rid));
        return favorite != null;
    }

    /**
     * 添加收藏
     * @param uid
     * @param rid
     */
    @Override
    public void add(int uid, String rid) {
        favoriteDao.add(uid,Integer.parseInt(rid));
    }
}
