Hello文件编译后，首先我发现，JDK11和JDK8生成的字节码千差万别
这里为与课堂内容一致，使用JDK8编译而成的版本

```
  public static void main(java.lang.String[]);
    Code:
       0: iconst_1               
       1: istore_1
       2: iconst_2
       3: istore_2               // 声明了2个常量
       4: iload_1
       5: iload_2
       6: iadd                   
       7: istore_3               // 相加
       8: iload_1
       9: iload_2
      10: isub
      11: istore        4        // 相减
      13: iload_1
      14: iload_2
      15: imul
      16: istore        5        // 相乘
      18: iload_1
      19: iload_2
      20: idiv
      21: istore        6        // 相除
      23: iload_1
      24: iload_2
      25: irem
      26: istore        7        // 取模
      28: iload         4
      30: iconst_m1
      31: if_icmpne     36       // if判断，如不满足，取36行，否则顺序执行
      34: iload_1
      35: istore_3
      36: iconst_0               // for判断
      37: istore        8
      39: iload         8
      41: iload_1
      42: if_icmpge     54       // 如果不满足for条件，去54行
      45: iinc          1, 1     
      48: iinc          8, -1    // k--
      51: goto          39       // for嵌套体执行完毕，回39行判断条件是否满足进入下一回循环
      54: return
```