from ..FlaskApp import app
from flask import jsonify


@app.route("/api")
def api_root():
    return jsonify({
        "success": True
    })
