package br.com.starterpack.mail;

public interface IMailProcessor {
    void process(final Mail mail) throws Exception;
}
