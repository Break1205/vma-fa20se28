package com.fa20se28.vma.component.impl;

import com.fa20se28.vma.component.DocumentComponent;
import com.fa20se28.vma.mapper.UserDocumentMapper;
import com.fa20se28.vma.mapper.UserDocumentTypeMapper;
import com.fa20se28.vma.model.UserDocument;
import com.fa20se28.vma.model.UserDocumentType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DocumentComponentImpl implements DocumentComponent {
    private final UserDocumentMapper userDocumentMapper;
    private final UserDocumentTypeMapper userDocumentTypeMapper;

    public DocumentComponentImpl(UserDocumentMapper userDocumentMapper, UserDocumentTypeMapper userDocumentTypeMapper) {
        this.userDocumentMapper = userDocumentMapper;
        this.userDocumentTypeMapper = userDocumentTypeMapper;
    }

    @Override
    public List<UserDocument> findUserDocumentById(String id) {
        return userDocumentMapper.findUserDocumentByUserId(id);
    }

    @Override
    public List<UserDocument> getUserDocuments() {
        return userDocumentMapper.getUserDocuments();
    }

    @Override
    public List<UserDocumentType> getUserDocumentTypes() {
        return userDocumentTypeMapper.getUserDocumentTypeList();
    }
}
