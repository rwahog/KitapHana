package com.kitaphana.dao;

import com.kitaphana.Database.Database;
import com.kitaphana.Entities.AVMaterial;
import com.kitaphana.Entities.JournalArticle;
import com.kitaphana.exceptions.DocumentNotFoundException;
import com.kitaphana.exceptions.OperationFailedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class avMaterialDAOImpl {

    Database db = Database.getInstance();
    commonDAOImpl commonDAO = new commonDAOImpl();

    private static final String FIND_ALL = "SELECT * FROM av";
    private static final String FIND_BY_ID = "SELECT * FROM av WHERE id=?";
    private static final String UPDATE = "UPDATE books SET title=? WHERE id=?";
    private static final String UPDATE_BY_ID_DOCUMENT = "UPDATE av SET title=? WHERE document_id=?";
    private static final String INSERT = "INSERT INTO av (title, document_id) VALUES (?,?)";

    public AVMaterial findByDocumentId(String id) {
        return (AVMaterial) commonDAO.findByDocumentId(id, "av");
    }

    public AVMaterial findById(String id) {
        AVMaterial avMaterial;
        try {
            PreparedStatement ps = db.connect().prepareStatement(FIND_BY_ID);
            ps.setLong(1, Long.parseLong(id));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                avMaterial = new AVMaterial();
                avMaterial.setTitle(rs.getString("title"));
                avMaterial.setDocumentId(rs.getLong("document_id"));
            } else {
                throw new DocumentNotFoundException();
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return avMaterial;
    }

    public ArrayList<AVMaterial> findAll() {
        ArrayList<AVMaterial> avMaterials = new ArrayList<>();

        try {
            PreparedStatement ps = db.con.prepareStatement(FIND_ALL);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AVMaterial avMaterial = new AVMaterial();

                avMaterial.setTitle(rs.getString("title"));
                avMaterial.setDocumentId(rs.getLong("document_id"));
                avMaterials.add(avMaterial);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return avMaterials;
    }

    public void insert(AVMaterial avMaterial) {
        try {
            PreparedStatement ps = db.connect().prepareStatement(INSERT);
            ps.setString(1, avMaterial.getTitle());
            ps.setLong(2, avMaterial.getId());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

    public void update(AVMaterial avMaterial) {
        try {
            PreparedStatement ps = db.connect().prepareStatement(UPDATE);
            setVariables(avMaterial, ps);

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

    public void delete(long id) {
        commonDAO.delete(id, "av", "document_id");
    }

    private AVMaterial getVariables(ResultSet rs) {
        AVMaterial avMaterial;
        try {
            avMaterial = new AVMaterial();
            avMaterial.setTitle(rs.getString("title"));
            avMaterial.setDocumentId(rs.getLong("document_id"));
        } catch (SQLException e) {
            throw new OperationFailedException();
        }
        return avMaterial;
    }

    private void setVariables(AVMaterial avMaterial, PreparedStatement ps) {
        try {
            ps.setString(1, avMaterial.getTitle());
            ps.setLong(2, avMaterial.getDocumentId());

        } catch (SQLException e) {
            throw new OperationFailedException();
        }
    }

}
