package tigers.cave.webm.invoice.dao.common.constant;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum用デコーダークラス.
 *
 * @param <K> コード値
 * @param <V> バリュー
 */
public class EnumDecoder<K extends Serializable, V extends EnumEncodable<K>> {

  /** マップ定義. */
  private Map<K, V> map;

  /**
   * デコーダー.
   *
   * @param values Enum群
   */
  private EnumDecoder(V[] values) {
    map = new HashMap<K, V>(values.length);

    for (V value : values) {
      V old = map.put(value.getCode(), value);

      // コード値の重複はサポートしない
      if (old != null) {
        throw new IllegalArgumentException("duplicated code: " + value);
      }
    }
  }

  /**
   * デコーダー.
   *
   * @param code コード値
   * @return Enumクラス
   */
  public V decode(K code) {
    return map.get(code);
  }

  /**
   * 型引数の指定を省略するための定義.
   *
   * @param <L> コード値
   * @param <W> バリュー
   * @param values Enum群
   * @return デコーダー
   */
  public static <L extends Serializable, W extends EnumEncodable<L>> EnumDecoder<L, W> create(
      W[] values) {
    return new EnumDecoder<L, W>(values);
  }

}
