package com.example.transaction_service.mapper;

import com.example.transaction_service.dto.DepositResponseDto;
import com.example.transaction_service.dto.TransactionResponseDto;
import com.example.transaction_service.dto.TransferCreateResponseDto;
import com.example.transaction_service.dto.WithdrawResponseDto;
import com.example.transaction_service.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Service
public class TransactionMapper {

    public TransferCreateResponseDto toTransferCreateResponseDto(Transaction transaction){

        TransferCreateResponseDto responseDto = new TransferCreateResponseDto();
        responseDto.setTransactionId(transaction.getId().toString());
        responseDto.setStatus(transaction.getStatus().toString());
        responseDto.setMessage("Transaction between two Account is successful");

        return responseDto;

    }


    public DepositResponseDto depositResponseDto(Transaction transaction){

        DepositResponseDto responseDto = new DepositResponseDto();
        responseDto.setTransactionId(transaction.getId().toString());
        responseDto.setStatus(transaction.getStatus().toString());
        responseDto.setMessage("Deposit to account is successful");

        return responseDto;
    }

    public WithdrawResponseDto withdrawResponseDto(Transaction transaction){

        WithdrawResponseDto responseDto = new WithdrawResponseDto();
        responseDto.setTransactionId(transaction.getId().toString());
        responseDto.setStatus(transaction.getStatus().toString());
        responseDto.setMessage("Deposit to account is successful");

        return responseDto;
    }

    public TransactionResponseDto transactionResponseDto(Transaction transaction){
        TransactionResponseDto responseDto = new TransactionResponseDto();

        responseDto.setTransactionId(transaction.getId().toString());
        responseDto.setStatus(transaction.getStatus().toString());
        responseDto.setType(transaction.getType().toString());
        responseDto.setAmount(transaction.getAmount());
        responseDto.setTimestamp(transaction.getTransactionTime().toString());

        return responseDto;
    }
}
