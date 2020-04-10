package com.lxq18.learn.serialize.protobuf.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.googlecode.protobuf.format.JsonFormat;
import com.lxq18.learn.serialize.protobuf.protocol.AddressBookProtos.AddressBook;
import com.lxq18.learn.serialize.protobuf.protocol.AddressBookProtos.Person;

/**
 * @author lixiaoqiang
 * @create 2020/4/6 14:19
 */
public class AddPerson {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        AddressBook.Builder addressBook = AddressBook.newBuilder();
        for (int i = 1; i < 3; i++) {
            Person.PhoneNumber.Builder homePhone = Person.PhoneNumber.newBuilder();
            homePhone.setType(Person.PhoneType.HOME)
                    .setNumber("010-12345678");

            Person.PhoneNumber.Builder workPhone = Person.PhoneNumber.newBuilder();
            workPhone.setType(Person.PhoneType.WORK)
                    .setNumber("12345678");

            Person.Builder person = Person.newBuilder();
            person.setId(i)
                    .setEmail(i + "@163.com")
                    .setName("name" + i);

            person.addPhones(homePhone)
                    .addPhones(workPhone);


            addressBook.addPeople(person);
        }

        //序列化
        byte[] toBytes = addressBook.build().toByteArray();

        //反序列化
        AddressBook addressBook1 = AddressBook.parseFrom(toBytes);
        System.out.println(addressBook1.toString());

        //protobuf转json
        String json = JsonFormat.printToString(addressBook.build());
        System.out.println("protobuf转json = " + json);

        //json转protobuf
        AddressBook.Builder protobufFromJson = AddressBook.newBuilder();
        try {
            JsonFormat.merge(json, protobufFromJson);
        } catch (JsonFormat.ParseException e) {
            e.printStackTrace();
        }
        System.out.println("json转protobuf = " + JsonFormat.printToString(protobufFromJson.build()));
    }
}





