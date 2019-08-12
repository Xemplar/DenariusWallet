# Denarius Wallet
This is the official repo for the mobile denarius wallet. You will be able to do may things here as you can do on the QT wallet. Only thing is that many people will be sharing two nodes, one for your standard wallet and one for staking. Features you can expect:
- Multiple addresses that you can use for whatever
- Abillity to stake right from your phone
- Proof of Data
- Have the option to keep priv keys on your phone rather than in the node
- Have the option to have your priv keys mailed to you (for the cost of mailing)
- Password/Fingerprint protection for payments, staking and access to the app
- Address book and messages
- Block explorer

## Current progress
We have the following currently working:
- Server communticates with client
- Client can send commands to server such as `getinfo` and `getblockheight`
- Client can securely communicate with server for account creation and login
- Server remebers clients using SQLite

## Next Steps
The following are what we are working on right now
- Requesting new addresses
- Paying with addresses

## To Do
- Work on staking process
- Block explorere
- *more to come*
