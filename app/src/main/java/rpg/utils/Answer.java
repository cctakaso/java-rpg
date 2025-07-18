package rpg.utils;

public class Answer<T> {
  private int index;
  private String label;
  private T value;
  private String info;

  public Answer() {
    this(new String(), null, new String());
  }

  public Answer(String label) {
    this(label, null, "");
  }
  public Answer(String label, T value) {
    this(label, value, "");
  }
  public Answer(String label, T value, String info) {
    //this.index = 0;
    this.label = label;
    this.value = value;
    this.info = info;
  }

  public int getIndex() {
      return index;
  }
  public void setIndex(int index) {
      this.index = index;
  }

  public String getLabel() {
      return label;
  }
  public T getValue() {
      return value;
  }
  public String getInfo() {
      return info;
  }
}
