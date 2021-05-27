package com.rp.sec01;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Supplier;

import com.rp.courseutil.Util;

import reactor.core.publisher.Mono;

public class AssignmentByme {

    public static void main(String[] args) throws IOException {
        Supplier<String> fileReadingSupplier = () -> read();
        Mono<String> fileReadingMono = Mono.fromSupplier(fileReadingSupplier);

        fileReadingMono.subscribe(
                Util.onNext(), Util.onError(), Util.onComplete()
        );
    }

    public static String read() {
        String file ="src/main/resources/assignment/sec01/file01.txt";

        BufferedReader reader = null;
        String currentLine = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            currentLine = reader.readLine();
            reader.close();
        } catch (IOException e) {
            // never supress exception, just throw so that subscriber will get notified
            throw new RuntimeException(e);
        }
        return currentLine;
    }

}
