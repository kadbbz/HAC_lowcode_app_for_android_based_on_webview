package com.huozige.lab.container.utilities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.elvishew.xlog.XLog;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Nullable;

/**
 * 全局的广播派发器
 * 减少性能开销，避免摘除广播监听器时错误（摘除一个监听器时会自动摘掉所有同action的）
 */
public class BroadcastDispatcher {

    @SuppressLint("StaticFieldLeak")
    private static final BroadcastDispatcher INSTANCE = new BroadcastDispatcher();

    private BroadcastDispatcher() {
    }

    private Context _context;
    private final HashMap<UUID, BroadcastHandler> _handlers = new HashMap<>();
    private final HashMap<String, BroadcastReceiver> _receivers = new HashMap<>();

    /**
     * 初始化广播派发器
     *
     * @param context 用来监听广播的上下文，通常为Application
     */
    public void init(Context context) {
        _context = context;
    }

    /**
     * 获取派发器实例
     *
     * @return 可操作的实例
     */
    public static BroadcastDispatcher getInstance() {
        return INSTANCE;
    }

    /**
     * 注册监听处理器
     *
     * @param handler 处理器
     */
    public void register(BroadcastHandler handler) {

        // 先保存处理器
        _handlers.put(handler.getID(), handler);

        // 如果该处理器监听的Action没有被处理过，需要初始化一个新的监听器
        if (!_receivers.containsKey(handler.getAction())) {

            // 将监听器存放到缓存中
            _receivers.put(handler.getAction(), new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    // 监听器的逻辑是筛选出该Action的处理器，排序后逐个执行
                    var handlers = _handlers.values().stream().filter(d -> (Objects.equals(d.getAction(), handler.getAction()))).sorted(Comparator.comparingInt(BroadcastHandler::getPriority));

                    for (BroadcastHandler item : handlers.collect(Collectors.toList())
                    ) {
                        try {
                            var result = item.handle(intent.getExtras());

                            // 如果处理器要求中止，那就停止循环
                            if (result) {
                                break;
                            }
                        } catch (Exception ex) {
                            // 记录日志，继续往下走
                            XLog.e("执行广播消息处理器时发生错误，处理器ID：" + item.getID() + "，监听Action：" + item.getAction() + "，错误：" + ex);
                        }
                    }
                }
            });

            // 将监听器注册到系统中
            _context.registerReceiver(_receivers.get(handler.getAction()), new IntentFilter(handler.getAction()));
        }
    }

    /**
     * 移除特定处理器
     *
     * @param handlerId 需要移除的处理器ID
     */
    public void unregister(UUID handlerId) {

        // 首先摘除处理器，确保不会派发新的广播
        BroadcastHandler currentHandler = _handlers.remove(handlerId);

        if (currentHandler != null) {

            // 检查该Action下是否还有其他处理器
            if (_handlers.values().stream().noneMatch(d -> (Objects.equals(d.getAction(), currentHandler.getAction())))) {

                // 对于摘除最后一个处理器时，需要停掉这个监听器
                BroadcastReceiver currentReceiver = _receivers.remove(currentHandler.getAction());

                // 停止监听器，需要处理异常，如该监听器已经被杀掉等
                try {
                    _context.unregisterReceiver(currentReceiver);
                } catch (Exception ex) {
                    XLog.e("停止广播监听器时发生错误：" + ex);
                }
            }
        }
    }

    /**
     * 移除特定处理器
     *
     * @param action 需要移除的Action名称
     */
    public void unregister(String action) {

        // 首先摘除监听器
        BroadcastReceiver currentReceiver = _receivers.remove(action);

        if (currentReceiver != null) {
            // 停止监听器，需要处理异常，如该监听器已经被杀掉等
            try {
                _context.unregisterReceiver(currentReceiver);
            } catch (Exception ex) {
                XLog.e("停止广播监听器时发生错误：" + ex);
            }

            // 遍历处理器，筛选出指定Action的
            ArrayList<UUID> removable = new ArrayList<>();
            for (Map.Entry<UUID, BroadcastHandler> entity : _handlers.entrySet()
            ) {
                if (Objects.equals(entity.getValue().getAction(), action)) {
                    removable.add(entity.getKey());
                }
            }

            // 从处理器列表中移除
            for (UUID id : removable) {
                _handlers.remove(id);
            }
        }
    }

    /**
     * 广播处理器的抽象类，用来定义接收到广播时的业务逻辑
     */
    public static abstract class BroadcastHandler {

        private final UUID _id = UUID.randomUUID();

        /**
         * 获取监听的Action
         *
         * @return Action
         */
        public abstract String getAction();

        /**
         * 处理时的优先级，默认为0
         *
         * @return 优先级
         */
        public int getPriority() {
            return 0;
        }

        /**
         * 处理器的唯一标识
         *
         * @return 唯一标识
         */
        public UUID getID() {
            return _id;
        }

        /**
         * 抽象方法：处理广播消息
         *
         * @param extras 广播中包含的Extra数据
         * @return 是否立即中断处理，如果为是，则意味着后续的处理器将不会被触发
         */
        public abstract boolean handle(@Nullable Bundle extras);
    }


}


