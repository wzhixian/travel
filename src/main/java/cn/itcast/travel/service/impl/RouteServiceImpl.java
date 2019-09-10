package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImgDao routeImgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    /**
     * 分页查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize,String rname) {
        //封装PageBean
        PageBean<Route> pb = new PageBean<>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示条数
        pb.setPageSize(pageSize);
        //设置总记录数
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);

        //设置当前页显示的数据集合
        int start = (currentPage - 1) * pageSize;//开始记录数
        List<Route> lists = routeDao.findByPage(cid,start,pageSize,rname);
        pb.setLists(lists);


        //设置总页数
        int totalpage = totalCount % pageSize == 0 ? totalCount/pageSize : (totalCount/pageSize) + 1;
        pb.setTotalPage(totalpage);
        return pb;
    }

    /**
     * 根据id查询一个数据
     * @param ridStr
     * @return
     */
    @Override
    public Route findOne(String ridStr) {
        int rid = Integer.parseInt(ridStr);
        //根据id去route表中查询route对象
        Route route = routeDao.findOne(rid);

        //根据route的id 查询图片集合信息
        List<RouteImg> routeImgs = routeImgDao.finByRid(rid);
        //将集合设置到route对象
        route.setRouteImgList(routeImgs);

        //根据route的sid商家id查询商家对象
        Seller seller = sellerDao.findByid(route.getSid());
        //将seller设置打route
        route.setSeller(seller);

        //查询收藏数量
        int count = favoriteDao.findCountByRid(rid);
        route.setCount(count);
        return route;
    }
}
