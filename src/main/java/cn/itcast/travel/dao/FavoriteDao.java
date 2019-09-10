package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    /**
     * 根据uid和rid查询用户收藏信息
     * @param uid
     * @param rid
     * @return
     */
    public Favorite findByUidAndRid(int uid,int rid);

    /**
     * 根据rid查询收藏数量
     * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param uid
     * @param rid
     */
    void add(int uid, int rid);
}
