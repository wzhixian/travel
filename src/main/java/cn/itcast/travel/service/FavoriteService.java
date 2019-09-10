package cn.itcast.travel.service;

public interface FavoriteService {
    /**
     * 判断用户是否收藏
     * @param rid
     * @param uid
     * @return
     */
    public boolean isFavorite(int uid,String rid);

    /**
     * 添加收藏
     * @param uid
     * @param rid
     */
    void add(int uid, String rid);
}
