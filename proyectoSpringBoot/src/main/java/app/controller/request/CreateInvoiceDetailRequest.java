/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author andre
 */
@Getter
@Setter
@NoArgsConstructor
public class CreateInvoiceDetailRequest {
    private String product;
    private String productCost;
    private String quantity;
    private String UserNamePartner;
    private String document;
}
