package com.cnh.frame.wraps;

import com.cnh.mvc.system.adminInfo.entity.AdminInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * ${Description}
 *
 * @author Administrator
 * @version v1.0.0
 * @since 2016/11/18
 */
public class CollectionWrap {

    /**
     * 集合JOIN
     * @param list
     * @return
     */
    public static String join(final List<?> list) {
        return join(list, ",");
    }

    /**
     * 集合JOIN
     * @param list
     * @param split
     * @return
     */
    public static String join(final List<?> list, final String split) {
        return join(list, split, new JoinProcessor<Object>() {
            @Override
            public String process(Object value, int index, int size) {
                return String.valueOf(value) + (index < size - 1 ? split : "");
            }
        });
    }

    /**
     * 集合JOIN
     * @param list
     * @param split
     * @param joinProcessor
     * @return
     */
    public static String join(final List<?> list, final String split, final JoinProcessor joinProcessor) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, l = list.size(); i < l; i++) {
            sb.append(joinProcessor.process(list.get(i), i, l));
        }
        return sb.toString();
    }


    public interface JoinProcessor<E> {

        public String process(E value, int index, int size);

    }


    public static void main(String[] args) {
        List<AdminInfo> list = new ArrayList<AdminInfo>();
        for (int i = 0; i < 10; i++) {
            AdminInfo adminInfo = new AdminInfo();
            adminInfo.setAccount("aaa-" + i);
            adminInfo.setPassword("ppp-" + i);
            list.add(adminInfo);
        }
        System.out.println(join(list, ",", new JoinProcessor<AdminInfo>() {
            @Override
            public String process(AdminInfo value, int index, int size) {
                return "{" + value.getAccount() + "," + value.getPassword() + "}" + (index < size - 1 ? "," : "");
            }
        }));
    }

}
