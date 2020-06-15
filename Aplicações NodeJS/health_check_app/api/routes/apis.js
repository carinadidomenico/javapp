const express = require('express');
const router = express.Router();
const mysql = require('mysql');
const crypto = require('crypto');
const encryptHash = 'hashsupersecreta';


// rotas para operações na tabela Aplicacao

router.get('/', (req, res, next) => {
    SQLQuery().makeQuery('SELECT * FROM Aplicacao', res);
});

router.get('/:hash', (req, res) => {
    if (req.params.hash) {
        const idHash = req.params.hash;
        SQLQuery().makeQuery(`SELECT * FROM Aplicacao WHERE appHash='${idHash}'`, res);
    }
});

router.post('/', (req, res, next) => {
    const nome = req.body.nome.substring(0, 50);
    const website = req.body.website.substring(0, 150);
    const appHash = crypto.createHmac('sha256', encryptHash)  
                    .update(Date.now() + nome.substring(0, 10))  
                    .digest('hex');  


    if (!nome || !website) {
        res.status(500).json({
            message: 'Preencha todos os campos!'
        });
    } else {
        SQLQuery().simpleQuery(`SELECT * FROM Aplicacao WHERE website='${website}'`, results => {
            if (results.length > 0) {
                res.status(403).json({
                    message: 'Ja existe uma aplicação com esta url'
                });
            } else {
                SQLQuery().makeQuery(`INSERT INTO Aplicacao(appHash, nome, website) VALUES('${appHash}', '${nome}', '${website}')`, res);
            }
        });
    }   
});

router.patch('/:hash', (req, res) => {
    if (req.params.hash) {
        const nome = req.body.nome.substring(0, 50);
        const website = req.body.website.substring(0, 150);
        const idHash = req.params.hash;
        SQLQuery().makeQuery(`UPDATE Aplicacao SET nome='${nome}', website='${website}' WHERE appHash=${idHash}`, res);
    }
});

router.delete('/:hash', (req, res) => {
    if (req.params.hash) {
        SQLQuery().makeQuery(`DELETE FROM Aplicacao WHERE appHash= '${req.params.hash}'`, res);
    }
});



// rotas para operações na tabela Endpoint

router.get('/:hash/endpoint', (req, res, next) => {
    if (req.params.hash) {
        const fatherHash = req.params.hash;
        SQLQuery().makeQuery(`SELECT * FROM Endpoint WHERE aplicacao_hash='${fatherHash}'`, res);
    }
});

router.post('/:hash/endpoint', (req, res, next) => {
    if (req.params.hash) {
        const url = req.body.url;
        const fatherHash = req.params.hash;
        SQLQuery().simpleQuery(`SELECT * FROM Endpoint WHERE url='${url}'`, results => {
            if (results.length > 0) {
                res.status(403).json({
                    message: 'Esta rota ja existe'
                });
            } else {
                SQLQuery().simpleQuery(`SELECT idApp FROM Aplicacao WHERE appHash='${fatherHash}'`, results => {
                    const fatherID = results[0].idApp
                    SQLQuery().makeQuery(`INSERT INTO Endpoint(url, aplicacao_id, aplicacao_hash) VALUES('${url}', ${fatherID}, '${fatherHash}')`, res);
                });
            }
        });
    }
});

router.patch('/:hash/endpoint/:idEndpoint', (req, res) => {
    if (req.params.hash && req.params.idEndpoint) {
        const url = req.body.url;
        const fatherHash = req.params.hash;
        const idEndpoint = req.params.idEndpoint;
        SQLQuery().simpleQuery(`SELECT * FROM Endpoint WHERE url='${url}'`, results => {
            if (results.length > 0) {
                res.status(403).json({
                    message: 'Usuario digitou a mesma coisa.'
                });
            } else {
                SQLQuery().makeQuery(`UPDATE Endpoint SET url='${url}' WHERE idEndpoint=${idEndpoint} AND aplicacao_hash='${fatherHash}'`, res);
            }
        });
    }
});

router.delete('/:hash/endpoint/:idEndpoint', (req, res) => {
    if (req.params.hash && req.params.idEndpoint) {
        const fatherHash = req.params.hash;
        const idEndpoint = req.params.idEndpoint;
        SQLQuery().makeQuery(`DELETE FROM Endpoint WHERE idEndpoint=${idEndpoint} AND aplicacao_hash='${fatherHash}'`, res);
    }    
});


const SQLQuery = () => {
    connection = mysql.createConnection({
        host: 'localhost',
        port: 3308,
        user: 'root',
        password: '',
        database: 'moss'
    });
    return {
        makeQuery: (query, res) => {
            connection.query(query, (err, results, fields) => {
                if (err) {
                    res.status(500).json({
                        message: 'Erro no servidor',
                        erro: err
                    });
                } else {
                    res.status(200).json({
                        retorno: results
                    });
                }
                connection.end();
            });
        }, simpleQuery: (query, returnFN) => {
            connection.query(query, (err, results, fields) => {
                if (err) {
                    data.erro = 'Erro na requisição do ID';
                    return returnFN(err);
                } else {
                    return returnFN(results);        
                }
            });
        }, postHttp: (query, res) => {
            connection.query(query, (err, results, fields) => {
                if (err) {
                    res.status(500).json({
                        message: 'Erro no servidor',
                        erro: err
                    });
                } else {
                    res.status(200).json({
                        retorno: results
                    });
                }
                connection.end();
            });
        }
    }
}

module.exports = router;