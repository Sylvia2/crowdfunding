package com.kunlanw.design;

import com.kunlanw.design.contract.Fund;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DesignApplicationTests {

    private static final BigInteger GAS_LIMIT=BigInteger.valueOf(3_000_000L);
    private static final BigInteger GAS_PRICE=BigInteger.valueOf(20_000_000_000L);
    @Test
    public void contextLoads() throws Exception {
        System.out.println("hello");
        Web3j web3j = Web3j.build(new HttpService());
        Credentials credentials=Credentials.create("c8b85acb96659815be8127b825bf0252785b71cc57e163b94dcac7aebedd66dd");
        String address=credentials.getAddress();
        System.out.println(web3j.ethGasPrice().send().getGasPrice());
//        Fund fund=Fund.deploy(web3j,credentials,GAS_PRICE,GAS_LIMIT).send();
        String contractAddress="0x18daa5ab5720b394c11cf6e493550ef2946c4075";
        String address_to="0x76EE9b8B1A9FD86e4a235733dC0C9E9BAC202477";
        Fund fund=Fund.load(contractAddress,web3j,credentials,GAS_PRICE,GAS_LIMIT);
        System.out.println(fund.getContractAddress());
        TransactionReceipt receipt=fund.setSuccessAmount(BigInteger.valueOf(20),address_to,BigInteger.ZERO).send();
        System.out.println(fund.successAmount(address_to).send());
        System.out.println(receipt.getGasUsed());
        System.out.println(receipt.getTransactionHash());
        System.out.println(fund.total().send());
        System.out.println(fund.organizer().send());
    }

}
