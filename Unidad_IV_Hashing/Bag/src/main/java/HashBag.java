import java.util.HashMap;

public class HashBag<T> extends HashMap<T, Integer> implements Bag<T>{
    public int getCount(T value){
        if(!containsKey(value)){
            return 0;
        }
        return get(value);
    }
    public void add(T value){
        if(putIfAbsent(value, 1) != null){
            put(value, get(value) + 1);
        }
    }
    @Override
    public void removeBag(T value){
        if(containsKey(value)){
           if(get(value) == 1){
               remove(value, 1);
           }else{
               put(value, get(value) - 1);
           }
        }
    }

    public static void main(String[] args) {
        Bag<Integer> myBag = new HashBag<>();
        myBag.add(10);
        myBag.add(20);
        myBag.add(10);
        myBag.add(10);
        System.out.println(myBag.getCount(5));
        System.out.println(myBag.getCount(10));
    }
}
