package com.becmartin.xeroandroidprivate;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.services.RSASha1SignatureService;
import com.github.scribejava.core.services.SignatureService;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import android.content.Context;
import android.content.ContextWrapper;
import android.util.Base64;
import android.util.Log;

/**
 * Created by Bec Martin on 24/07/16.
 */
public class XeroAPI extends DefaultApi10a {


    Context context;

    public XeroAPI(Context context) {
        this.context = context;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return null;
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return null;
    }

    @Override
    public String getRequestTokenEndpoint() {
        return null;
    }


    @Override
    public SignatureService getSignatureService() {
        return new RSASha1SignatureService(getPrivateKey());
    }

    //This is an alternative that is currently not usable due to Android ASN1 encoding/decoding issues
    public PrivateKey get() {

        try{
            InputStream is = context.getAssets().open("xero_privatekey.pcks8");
            byte[] fileBytes=new byte[is.available()];
            is.read( fileBytes);
            is.close();


            PKCS8EncodedKeySpec spec =
                    new PKCS8EncodedKeySpec(fileBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePrivate(spec);
        }catch (Exception e){
            Log.e("API Exception", e.getLocalizedMessage());
        }

        return null;

    }

    private static PrivateKey getPrivateKey()
    {
        /*You can copy and past the key from your pcks8 file here
        keep the new line formatting and remove the header and footer
        you should replace this with your own private key and this has been replaced
        for my demo app - this is not ideal as it's not a super awesome idea
        to have this as a string unless you trust the phone
        this could be extracted from the apk on a maliciously rooted file*/
        String str = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANbj+20GjNLLwMCI\n" +
                "b8xzonEFxUDQFse8nYlJq49vCOesd86jH8Oa9kxe+BlrOGpzR8uQSsZGLxujLvo1\n" +
                "7VdDEefqydCl3R1NwEhwu8WZqCSuT/8i/4sPzF0cz2nfgvKs8WkGLfTTb1mNQe/Z\n" +
                "Lj+5PWZMtX8me9/Us7gwOYDCUUd3AgMBAAECgYBi84Q5RgTepFhTz/+7I5wvczrL\n" +
                "h4aV3vKj3zqSx8xP8gkRK+haiCbWL/0Kp2bJDeCHiiAEgRj8Hv5o1SA/SHEbg+KM\n" +
                "WgY+KnSN51NYuJc9/C2ogJXD4C3JKhWAOR1qfh/VSYEplTo1IfDH0crdUD178R7T\n" +
                "ibjnI7QD3V820CQJkQJBAO9gFPmr8ywnbPj4bL2vXTMfpgj1upARm4EP6PBJWhwd\n" +
                "f14dmNR9mHXz7K/6OzSvByrEwFedo6VB2VDrwBwxofUCQQDl0JQ4yMVqRfmvb1TB\n" +
                "B7RU3Z21zQnPgavXlN2QSUr3lo07OSLrP6RiICNokL6TFtg2u/ViD7e1q4ZU+IOG\n" +
                "oKQ7AkAiFdhmxOlXfDW0Lgut5u6qfPMzi2oJYbh25EETFh7SqhCc4jIE7+1pvlYR\n" +
                "qNwNYG7w1BtfXn8S5RGAyFEOsrKRAkEA0HoSvCCF1g58nwVj4WhX0tToCNBkIfYE\n" +
                "KaFGDWovs8LDhAIlBzvIp6t385e7CjwjECQaSkbv5MIhLlvzFOINmQJBAMevmnAH\n" +
                "vfT/ckEvUoAtvV++wNmRrUwIaNPDlwtPkcrFUZq+SVvhlXuecfawYSDJxEwN2B8d\n" +
                "i4VFLjCM3peONIQ=";

        try
        {
            KeyFactory fac = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(Base64.decode(str.getBytes(), Base64.DEFAULT));
            return fac.generatePrivate(privKeySpec);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
