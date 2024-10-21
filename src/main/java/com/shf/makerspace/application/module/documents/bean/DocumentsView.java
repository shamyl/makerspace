package com.shf.makerspace.application.module.documents.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentsView {
    private Long id;
    private String name;
    private String url;
    private Long moduleId;
    private String moduleType;
}
