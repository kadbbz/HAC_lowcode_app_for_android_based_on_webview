package com.huozige.lab.container.proxy.support.offlinecustomform.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class OfflineComputedHelper {
    // 固定 16 色主题色环。新增表单定义按导入顺序取色；重复导入同一项目编号时复用旧颜色。
    private static final String[] THEME_COLORS = {
            "#F44336", "#E91E63", "#9C27B0", "#673AB7",
            "#3F51B5", "#2196F3", "#03A9F4", "#00BCD4",
            "#009688", "#4CAF50", "#8BC34A", "#CDDC39",
            "#FFEB3B", "#FFC107", "#FF9800", "#FF5722"
    };

    // 根据导入顺序获取主题色，超过 16 个后从色环开头循环使用。
    public static String getThemeColor(int index) {
        return THEME_COLORS[Math.max(index, 0) % THEME_COLORS.length];
    }

    // 根据项目编号生成本地 identicon，不把图片写入文件或 JSON，避免 base64 占用空间。
    public static Bitmap createIconBitmap(String key, String themeColor) {
        byte[] hash = sha256(key == null ? "" : key);
        // 64x64 头像按 5x5 网格绘制；每个格子 12px，四周保留 2px 边距。
        int size = 64;
        int cell = 12;
        int offset = 2;
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(245, 245, 245));
        canvas.drawRect(0, 0, size, size, paint);
        paint.setColor(Color.parseColor(themeColor));

        // 只读取左侧 3 列的 hash 结果，右侧 2 列镜像生成，保证图案左右对称且稳定。
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 3; col++) {
                int hashIndex = row * 3 + col;
                if ((hash[hashIndex] & 1) == 0) {
                    continue;
                }
                drawCell(canvas, paint, col, row, cell, offset);
                if (col < 2) {
                    drawCell(canvas, paint, 4 - col, row, cell, offset);
                }
            }
        }

        return bitmap;
    }

    private static void drawCell(Canvas canvas, Paint paint, int col, int row, int cell, int offset) {
        int left = offset + col * cell;
        int top = offset + row * cell;
        canvas.drawRect(left, top, left + cell, top + cell, paint);
    }

    // 使用 SHA-256 让同一个 key 始终得到同一组格子填充结果，不依赖运行时随机数。
    private static byte[] sha256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(input.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
