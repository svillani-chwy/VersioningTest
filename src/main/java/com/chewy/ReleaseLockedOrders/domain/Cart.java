package com.chewy.ReleaseLockedOrders.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private String id;
    private String accountId;
    private String editorAccountId;
}
