# Xero-Private-Sample
Example code for Android integration with Xero API private application type.

This has sample keys in it which have been invalidated. You will need to create your own keys and Xero application to use it which you can do via [developer.xero.com](https://developer.xero.com)

For more on Xero private integration visit the [Private Applications](https://developer.xero.com/documentation/getting-started/private-applications/) page.

For more info on generating your own private keys visit the [Private Keypair](https://developer.xero.com/documentation/advanced-docs/public-private-keypair/ ) page.

# Instructions

Open **/app/src/main/res/values/strings.xml** and add 2 tags for api_key and api_secret from your private auth settings.

`<string name="api_key">ABCDEFGHIJKLMNOPQRSTUVWXYZ</string>`

`<string name="api_secret">ABCDEFGHIJKLMNOPQRSTUVWXYZ</string>`

Open **/app/src/main/java/com/becmartin/xeroandroidprivate/XeroAPI.java** and update the PrivateKey getPrivateKey() string with the key the private auth key remembering to make sure the key lines are split with a `\n`
