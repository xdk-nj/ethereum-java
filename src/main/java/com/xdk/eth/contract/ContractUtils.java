package com.xdk.eth.contract;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ContractUtils {

    public static void generateABIAndBIN() throws IOException {
        String abi = "[\n" +
                "\t{\n" +
                "\t\t\"constant\": false,\n" +
                "\t\t\"inputs\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"internalType\": \"uint256\",\n" +
                "\t\t\t\t\"name\": \"x\",\n" +
                "\t\t\t\t\"type\": \"uint256\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"name\": \"set\",\n" +
                "\t\t\"outputs\": [],\n" +
                "\t\t\"payable\": false,\n" +
                "\t\t\"stateMutability\": \"nonpayable\",\n" +
                "\t\t\"type\": \"function\"\n" +
                "\t},\n" +
                "\t{\n" +
                "\t\t\"constant\": true,\n" +
                "\t\t\"inputs\": [],\n" +
                "\t\t\"name\": \"get\",\n" +
                "\t\t\"outputs\": [\n" +
                "\t\t\t{\n" +
                "\t\t\t\t\"internalType\": \"uint256\",\n" +
                "\t\t\t\t\"name\": \"\",\n" +
                "\t\t\t\t\"type\": \"uint256\"\n" +
                "\t\t\t}\n" +
                "\t\t],\n" +
                "\t\t\"payable\": false,\n" +
                "\t\t\"stateMutability\": \"view\",\n" +
                "\t\t\"type\": \"function\"\n" +
                "\t}\n" +
                "]";
        String bin = "{\n" +
                "\t\"linkReferences\": {},\n" +
                "\t\"object\": \"608060405234801561001057600080fd5b5060c68061001f6000396000f3fe6080604052348015600f57600080fd5b506004361060325760003560e01c806360fe47b11460375780636d4ce63c146062575b600080fd5b606060048036036020811015604b57600080fd5b8101908080359060200190929190505050607e565b005b60686088565b6040518082815260200191505060405180910390f35b8060008190555050565b6000805490509056fea265627a7a7231582045109c98e174b1ec711ce501efe178101e0ce9c4440c3bad46b57b310670551c64736f6c63430005110032\",\n" +
                "\t\"opcodes\": \"PUSH1 0x80 PUSH1 0x40 MSTORE CALLVALUE DUP1 ISZERO PUSH2 0x10 JUMPI PUSH1 0x0 DUP1 REVERT JUMPDEST POP PUSH1 0xC6 DUP1 PUSH2 0x1F PUSH1 0x0 CODECOPY PUSH1 0x0 RETURN INVALID PUSH1 0x80 PUSH1 0x40 MSTORE CALLVALUE DUP1 ISZERO PUSH1 0xF JUMPI PUSH1 0x0 DUP1 REVERT JUMPDEST POP PUSH1 0x4 CALLDATASIZE LT PUSH1 0x32 JUMPI PUSH1 0x0 CALLDATALOAD PUSH1 0xE0 SHR DUP1 PUSH4 0x60FE47B1 EQ PUSH1 0x37 JUMPI DUP1 PUSH4 0x6D4CE63C EQ PUSH1 0x62 JUMPI JUMPDEST PUSH1 0x0 DUP1 REVERT JUMPDEST PUSH1 0x60 PUSH1 0x4 DUP1 CALLDATASIZE SUB PUSH1 0x20 DUP2 LT ISZERO PUSH1 0x4B JUMPI PUSH1 0x0 DUP1 REVERT JUMPDEST DUP2 ADD SWAP1 DUP1 DUP1 CALLDATALOAD SWAP1 PUSH1 0x20 ADD SWAP1 SWAP3 SWAP2 SWAP1 POP POP POP PUSH1 0x7E JUMP JUMPDEST STOP JUMPDEST PUSH1 0x68 PUSH1 0x88 JUMP JUMPDEST PUSH1 0x40 MLOAD DUP1 DUP3 DUP2 MSTORE PUSH1 0x20 ADD SWAP2 POP POP PUSH1 0x40 MLOAD DUP1 SWAP2 SUB SWAP1 RETURN JUMPDEST DUP1 PUSH1 0x0 DUP2 SWAP1 SSTORE POP POP JUMP JUMPDEST PUSH1 0x0 DUP1 SLOAD SWAP1 POP SWAP1 JUMP INVALID LOG2 PUSH6 0x627A7A723158 KECCAK256 GASLIMIT LT SWAP13 SWAP9 0xE1 PUSH21 0xB1EC711CE501EFE178101E0CE9C4440C3BAD46B57B BALANCE MOD PUSH17 0x551C64736F6C6343000511003200000000 \",\n" +
                "\t\"sourceMap\": \"28:184:0:-;;;;8:9:-1;5:2;;;30:1;27;20:12;5:2;28:184:0;;;;;;;\"\n" +
                "}";
        String abiFileName = "SimpleStorage.abi";
        String binFileName = "SimpleStorage.bin";
        BufferedOutputStream abiBos = new BufferedOutputStream(new FileOutputStream(new File(abiFileName)));
        BufferedOutputStream binBos = new BufferedOutputStream(new FileOutputStream(new File(binFileName)));
        abiBos.write(abi.getBytes());
        abiBos.flush();
        binBos.write(bin.getBytes());
        binBos.flush();

        abiBos.close();
        binBos.close();
    }

    public static void main(String[] args) throws IOException {
        generateABIAndBIN();
    }
}
