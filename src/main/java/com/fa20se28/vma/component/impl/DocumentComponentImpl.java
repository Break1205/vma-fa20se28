package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.DocumentComponent;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.model.UserDocument;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentComponentImpl implements DocumentComponent {
    private final UserDocumentMapper userDocumentMapper;

    public DocumentComponentImpl(UserDocumentMapper userDocumentMapper) {
        this.userDocumentMapper = userDocumentMapper;
    }

    @Override
    public List<UserDocument> findUserDocumentByUserId(String id) {
        return userDocumentMapper.findUserDocumentByUserId(id);
    }
}
