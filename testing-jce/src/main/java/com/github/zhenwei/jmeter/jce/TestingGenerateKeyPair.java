package com.github.zhenwei.jmeter.jce;

import com.github.zhenwei.jmeter.params.BaseParams;
import com.github.zhenwei.string.tools.StringTools;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * @author: zhangzhenwei
 * @description: 产生密钥对
 * @date: 2023/5/8  10:07
 * @since: 1.0.0
 */
public class TestingGenerateKeyPair implements JavaSamplerClient {

  String provider = null, keyAlg = null;
  int keyIndex = 1, keyLength = 256;

  @Override
  public void setupTest(JavaSamplerContext context) {
    provider = context.getParameter(BaseParams.provider);
    keyAlg = context.getParameter(BaseParams.keyAlg);
    keyIndex = context.getIntParameter(BaseParams.keyIndex);
  }

  @Override
  public SampleResult runTest(JavaSamplerContext context) {
    SampleResult result = new SampleResult();
    result.sampleStart();
    try {
      KeyPairGenerator generator = KeyPairGenerator.getInstance(keyAlg, provider);
      generator.initialize(keyLength);
      KeyPair keyPair = generator.generateKeyPair();
      result.setSuccessful(true);
    } catch (Exception e) {
      result.setSuccessful(false);
      e.printStackTrace();
      throw new RuntimeException(e);
    } finally {
      result.sampleEnd();
    }
    return result;
  }

  @Override
  public void teardownTest(JavaSamplerContext context) {

  }

  @Override
  public Arguments getDefaultParameters() {
    return null;
  }
}
