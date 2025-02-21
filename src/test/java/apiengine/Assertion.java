package apiengine;

import org.testng.Assert;

import com.apiautomation.model.ResponseItem;
import com.apiautomation.model.request.RequestItem;

public class Assertion {
    public void assertAddProduct(ResponseItem responseItem, RequestItem requestItem){
        Assert.assertEquals(responseItem.title,requestItem.title);
        Assert.assertEquals(responseItem.price,requestItem.price);
        Assert.assertEquals(responseItem.discountPercentage, requestItem.discountPercentage);
        Assert.assertEquals(responseItem.stock, requestItem.stock);
        Assert.assertEquals(responseItem.category, requestItem.category);
    }
}
